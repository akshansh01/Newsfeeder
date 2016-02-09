from __future__ import print_function
from alchemyapi import AlchemyAPI
import json
import newspaper
import sys

alchemyapi = AlchemyAPI()

#paper = newspaper.build('http://yourstory.com/2015/05/dilbole/')
#article = paper.articles[0]
#print (article.url)

db = open('story.txt','r')
out = open('output.txt','w')
demo_text = db.read()

response = alchemyapi.keywords('text', demo_text, {'sentiment': 1})

sentiment = alchemyapi.sentiment('text',demo_text)

if response["docSentiment"]["type"] == "positive":

	if response['status'] == 'OK':
	    print('## Response Object ##')
	    print(json.dumps(response, indent=4))

	    print('')
	    print('## Keywords ##')
	    for keyword in response['keywords']:
		print('text: ', keyword['text'].encode('utf-8'))
		out.write(keyword['text'].encode('utf-8'))
		out.write("$")
		print('relevance: ', keyword['relevance'])
		out.write(keyword['relevance'])
		out.write("\n")  
		print('sentiment: ', keyword['sentiment']['type'])
		if 'score' in keyword['sentiment']:
		    print('sentiment score: ' + keyword['sentiment']['score'])
		print('')
	else:
	    print('Error in keyword extaction call: ', response['statusInfo'])


else:
	print ("Irrelevent story due to negative sentiment")
