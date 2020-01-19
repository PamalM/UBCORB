# -*- coding: utf-8 -*-
import scrapy
from datetime import datetime
import re
import itertools  
import numpy as np

class RedditbotSpider(scrapy.Spider):
    name = 'redditbot'
    allowed_domains = ['https://bookings.ok.ubc.ca/studyrooms/day.php?csrf_token=2e6ecb407039cc384c2926093c2977713e75a5247120a5b9edafea665587013b/']
    start_urls = ['file:///C:/Users/usama101/AppData/Local/Temp/tmp55cklheu.html']

    def parse(self, response):
         #Extracting the content using css selectors
        
        date =  response.xpath('//*[@id="dwm"]/h2/text()').extract()
        print(date)
        area = response.xpath('//*[@id="area_select"]/option[@selected="selected"]/text()').extract()
        rooms = response.xpath('//*[@id="day_main"]/thead[1]/tr/th/a/text()').extract()
        time = response.xpath('//*[@id="day_main"]/tbody/tr/td[1]/div/a/text()').extract()
        taken = response.xpath('//*[@id="day_main"]/tbody/tr/td/div/a').extract()  
        #taken = response.xpath('//*[@id="day_main"]/tbody/tr[7]/td[3]/div/a[@title="[Private]"]').extract()
        #votes = response.css('.score.unvoted::text').extract()
        #times = response.css('time::attr(title)').extract()
        #comments = response.css('.comments::text').extract()
        
        datetimeObj = datetime.strptime(str(date), "['%A %d %B %Y']")
 
        # Get the date object from datetime object
        dateObj = datetimeObj.date()
        #Get area
        area_edited = re.sub(r'[\[\'\'\]]', '', str(area))
        
        #Give the extracted content row wise
        
        for item in itertools.zip_longest(time,rooms,taken):
            #room_edited = re.sub(r" ?\([^)]+\)", "", item[1])
            scraped_info = {
                    'date' : dateObj,
                    'time' : item[0],
                    'area' : area_edited,
                    'room' : item[1],
                    
                    'taken' : 'title="[Private]"' in item[2],
                    }
                
            '''
                taken_bool=[]
                i=0
                for room_occupied in item[4]:
                    if ('title="[Private]"' in room_occupied):
                        taken_bool[i]=1
                        i=i+1
                    else:
                        taken_bool[i]=0
                        i=i+1
                    
                 '''   
               
                    
                    
            '''
        
                scraped_info = {
                    'date' : dateObj,
                    'time' : item[1],
                    'area' : item[2],
                    'room' : room_edited,
                    #'taken' : taken_bool[0],
                    
                }
                
                '''

            #yield or give the scraped info to scrapy
            yield scraped_info
