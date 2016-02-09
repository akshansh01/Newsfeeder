import feedparser
import time
from subprocess import check_output
import sys
from bs4 import BeautifulSoup
import requests
import datetime
import logging
import inspect


current_time_millis = lambda: int(round(time.time() * 1000))
current_timestamp = current_time_millis()
inpfile= 'inp.txt'
db = 'outnews.txt'
fout = open(db,'a')
fin = open(inpfile,'r')

#url = 'http://chicagotribune.feedsportal.com/c/34253/f/622872/index.rss'
url2 = 'http://feeds.bbci.co.uk/news/world/rss.xml?edition=uk'
url6 = 'http://www.thehindu.com/?service=rss'

#url3 = raw_input("enter the webpage: ")
#url4 = url3.strip()


print current_timestamp
print "----------\n"

def get_rss_in_file(url):
	feed = feedparser.parse(url)
	count = len(feed.entries)
	check = feed.bozo
	dat = datetime.datetime.now().strftime("%d/%m/%Y")

	if feed and count > 0 and check == 0: # if check == 1 : error
	    try:
		for i in range(count):
		    date = dat 
		    feedurl = url 
		    feedtitle = feed.feed.title
		    entryurl = feed.entries[i].link
		    entrytitle = feed.entries[i].title
		    fout.write("TITLE:\n"+entrytitle.encode('utf-8')+"\n")
		    fout.write(entryurl+"\n")
                              
	    except Exception, err:
		print str(err)
	else:
		print("could not parse")


def get_rss_feed_link(url):
	cnt=0
	source_code = requests.get(url)
	plain_text = source_code.text
	soup = BeautifulSoup(plain_text,"lxml")
	for link in soup.find_all("link", {"type" : "application/rss+xml"}):
		href = link.get('href')
		print("RSS feed for " + url + "is -->" + str(href))
		rss_link = str(href)
		rss_link = rss_link.strip()
		print rss_link
		get_rss_in_file(rss_link.encode('utf-8'))
		cnt=cnt+1
	return cnt	

	
def get_feed(url):
	source_code = requests.get(url)
	plain_text = source_code.content
	soup = BeautifulSoup(plain_text, "lxml")
	links = soup.findAll('a', {'class': 'a-link-normal s-access-detail-page a-text-normal'})
	print len(links)
	for link in links:
	    title = link.get('title')
	    print title

def main():
	fcnt=0
	for line in fin:
		fcnt = fcnt +1
		print fcnt
		print "\n"
		print line
		url = line.strip()
		count = get_rss_feed_link(url)
		if count ==0:
			get_feed(url)
			print "No RSS feed found"
			
		else:	
			print("Done putting title in file")
			fout.write("\n--------------------\n\n")



if __name__ == "__main__":main()
