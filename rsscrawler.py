import lxml.html

urls= (
    'http://lrvick.net',
    'http://zenhabits.net/',
    'http://theoatmeal.com/',
    'http://botd.wordpress.com/',
    'http://informantpodcast.com/',
    'http://moo.com',
)

for url in urls:
    html = lxml.html.parse(url)
    feed = None
    title = None
    try:
        feed = html.xpath('//link[@type="application/rss+xml"]/@href')[0]
        if not 'http' in feed:
            feed = "%s%s" % (url,feed)
    except:
        pass
    title = html.find('.//title').text
    print ("%s | %s | %s" % (url, title, feed)).encode('unicode-escape')
