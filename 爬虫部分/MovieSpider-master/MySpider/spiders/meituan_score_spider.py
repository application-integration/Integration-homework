import scrapy
from MySpider.items import MeituanFilmScore

class MeituanSpider(scrapy.Spider):
    name = 'meituan1'
    start_urls = []
    user_agent = 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36'

    def start_requests(self):
        request_url = 'https://maoyan.com/films'
        return [scrapy.FormRequest(request_url, callback=self.parse_movie)]

    def parse_movie(self, response):
        movie_list = response.xpath('//dl[@class="movie-list"]//dd')
        for index, dd in enumerate(movie_list):
            movie_name = dd.xpath('.//div[@class="channel-detail movie-item-title"]/@title').extract_first()
            before = dd.xpath('.//div[@class="channel-detail channel-detail-orange"]//i[@class="integer"]/text()').extract_first()
            after = dd.xpath('.//div[@class="channel-detail channel-detail-orange"]//i[@class="fraction"]/text()').extract_first()
            score = MeituanFilmScore(name=movie_name, score=before+after)
            yield score
