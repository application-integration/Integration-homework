import scrapy
from MySpider.items import TaopiaoFilmScore

class TaopiaoSpider(scrapy.Spider):
    name = 'taopiao_score'
    start_urls = []
    user_agent = 'Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:66.0) Gecko/20100101 Firefox/66.0 '

    def start_requests(self):
        prefix = 'https://dianying.taobao.com/showList.htm?spm=a1z21.3046609.header.4.32c0112a6H8N9h&n_s=new'
        next_request = prefix
        return [scrapy.FormRequest(next_request, headers={'User-Agent': self.user_agent},
                                   callback=self.parse_score)]

    def parse_score(self, response):
        prefix = response.url
        flexslider = response.xpath('//div[@class="movie-card-name"]')
        for index, li in enumerate(flexslider):
            movie_name = li.xpath('.//span[@class="bt-l"]/text()').extract_first()
            movie_score = li.xpath('.//span[@class="bt-r"]/text()').extract_first()
            score = TaopiaoFilmScore(name=movie_name, score=movie_score)
            yield score