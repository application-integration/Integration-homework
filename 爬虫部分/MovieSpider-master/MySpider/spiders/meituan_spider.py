import scrapy
from MySpider.items import MeituanFilm
from MySpider.items import MeituanFilmScreenings

class MeituanSpider(scrapy.Spider):
    name = 'meituan'
    start_urls = []
    price_num_dict = {' -14px -26px': '2', ' -46px -26px': '8'}
    user_agent = 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36'

    def start_requests(self):
        request_url = 'https://maoyan.com/films'
        return [scrapy.FormRequest(request_url, callback=self.parse_movie)]

    def parse_movie(self, response):
        prefix = response.url
        movie_list = response.xpath('//dl[@class="movie-list"]//dd')
        for index, div in enumerate(movie_list):
            movie_name = div.xpath('.//div[@class="channel-detail movie-item-title"]/@title').extract_first()

            movie_id = div.xpath('.//div[@class="movie-item"]/a/@href').extract_first().split('/')[-1]
            next_request = 'https://maoyan.com/cinemas?movieId='+movie_id
            yield scrapy.Request(next_request, callback=self.parse_cinema)

    # def parse_cinema(self, response):
    #     cinema_list = response.xpath('//div[@data-component="cinema-list"]//textarea//a[@class="link--black__green"]/@href').extract()
    #     items = []
    #     for cinema_url in cinema_list:
    #         result = scrapy.Request('http:'+cinema_url, callback=self.parse_cinema_detail)
    #         items.append(result)
    #     next_page_url = response.xpath('//ul[@class="paginator paginator--notri paginator--large"]//a[@gaevent="content/page/next"]//@href').extract_first()
    #     if next_page_url!=None:
    #         result = scrapy.Request(next_page_url, callback=self.parse_cinema)
    #         items += result
    #     print(items)
    #     return items

    def parse_cinema(self, response):
        page_urls = []
        page_urls.append(response.url)
        pages = response.xpath('//div[@class="cinema-pager"]//a')
        for index, a in enumerate(pages):
            text = a.xpath('./text()').extract_first()
            if(len(text)==1 and text!='1'):
                page_urls.append(a.xpath('./@href').extract_first())
        for i in range(len(page_urls)):
            if i ==0 :
                yield scrapy.Request(page_urls[i], callback=self.parse_cinema_page)
            else:

                yield scrapy.Request('https://maoyan.com/cinemas'+page_urls[i], callback=self.parse_cinema_page)

    def parse_cinema_page(self, response):
        cinema_list = response.xpath('//div[@class="cinemas-list"]//div[@class="cinema-cell"]')
        for index,div in enumerate(cinema_list):
            next_request = div.xpath('.//div[@class="cinema-info"]//a/@href').extract_first()
            yield scrapy.Request("https://maoyan.com"+next_request, headers={'User-Agent': self.user_agent},
                                 callback=self.parse_cinema_detail)

    def parse_cinema_detail(self, response):
        items = []
        cinema_name = response.xpath('//div[@class="cinema-brief-container"]/h3/text()').extract_first()
        cinema_name = cinema_name.replace('\n', '')
        movie_list = response.xpath('.//div[@class="movie-list"]//div')
        temp = ''
        for index, div in enumerate(movie_list):
            id = div.xpath('./@data-movieid').extract_first()
            mid = response.url.split('=')[-1]
            if id == mid :
                temp = div.xpath('./@data-index').extract_first()
                break
        str1 = './/div[@class="container"]/div[@data-index ="'+temp+'"]'
        info = response.xpath(str1)
        movie_name = info.xpath('.//div[@class="movie-info"]/div/h3/text()').extract_first()

        datelist = []
        date_list = info.xpath('.//div[@class="show-date"]//span')
        for index, span in enumerate(date_list):
            datelist.append(span.xpath('./text()').extract_first())
        table_list = info.xpath('.//table')
        for index, table in enumerate(table_list):
            date = datelist[index+1]
            screenings = []
            rows = table.xpath('.//tr')
            for j, row in enumerate(rows):
                if j!=0:
                    time = row.xpath('.//span[@class="begin-time"]/text()').extract_first()
                    auditorium = row.xpath('.//span[@class="hall"]/text()').extract_first()
                    type = row.xpath('.//span[@class="lang"]/text()').extract_first()
                    screening = MeituanFilmScreenings(auditorium=auditorium, time=time, type=type)
                    screenings.append(screening)
            film = MeituanFilm(name=movie_name.strip(), cinema=cinema_name.strip(), date=date, screenings=screenings)
            items.append(film)
        return items