import scrapy
from MySpider.items import NuomiFilmScore

class NuomiSpider(scrapy.Spider):
    name = 'nuomi1'
    start_urls = []
    cityId = '315'
    user_agent = 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36'

    def start_requests(self):
        prefix = 'https://dianying.nuomi.com/index'
        next_request = prefix + '?cityId=' + self.cityId
        return [scrapy.FormRequest(next_request, headers={'User-Agent': self.user_agent},
                                   callback=self.parse_score)]

    def parse_score(self, response):
        prefix = response.url
        flexslider = response.xpath('//div[@class="flexslider movielist"]//li')
        for index, li in enumerate(flexslider):
            movie_name = li.xpath('.//p[@class="text font14"]/text()').extract_first()
            movie_score = li.xpath('.//span[@class="fr record nuomi-orange"]/text()').extract_first()
            score = NuomiFilmScore(name=movie_name, score=movie_score)
            yield score