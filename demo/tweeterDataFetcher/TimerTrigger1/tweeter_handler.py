import tweepy
from textblob import TextBlob 
import logging
import datetime
import time

politic_set = [
    ['VOX','ROCÍO MONASTERIO'],
    ['PP','ISABEL DIAZ AYUSO'],
    ['PSOE','ANGEL GABILONDO'],
    ['CIUDADANOS','Cs','IGNACIO AGUADO'],
    ['MÁS MADRID','ÍÑIGO ERREJON'],
    ['PODEMOS','PABLO IGLESIAS']
]

def get_politic(minute):
    return politic_set[(minute//5)%len(politic_set)]

def datetime_from_utc_to_local(utc_datetime):
    now_timestamp = time.time()
    offset = datetime.datetime.fromtimestamp(now_timestamp) - datetime.datetime.utcfromtimestamp(now_timestamp)
    return utc_datetime + offset

def get_tweets(config, politic_search):
    tweets = []
    auth = tweepy.OAuthHandler(config['consumer_key'], config['consumer_secret'])
    auth.set_access_token(config['access_token'], config['access_token_secret'])
    api = tweepy.API(auth, wait_on_rate_limit=True,
                wait_on_rate_limit_notify=True)
    query_str = ' OR '.join(politic_search) + ' AND  -filter:retweets'
    logging.info('Searching in tweeter with query: %s',query_str)
    for tweet in tweepy.Cursor(api.search, query_str, lang='es', exclude_replies=True, result_type="recent").items(int(config['tweets_number'])):
        try:
            tweet_raw = {}
            tweet_raw['id'] = tweet.id
            tweet_raw['politic_party'] = politic_search[0]
            tweet_raw['created_at'] = datetime_from_utc_to_local(tweet.created_at).strftime("%Y-%m-%d %H:%M:%S")
            tweet_raw['user'] = {}
            tweet_raw['text'] = stripNonAlphaNum(tweet.text)
            tweet_raw['coordinates'] = tweet.coordinates
            tweet_raw['source'] = tweet.source
            tweet_raw['place'] = tweet.place
            tweet_raw['retweet_count'] = tweet.retweet_count
            tweet_raw['favorite_count'] = tweet.favorite_count
            tweet_raw['user']['id'] = tweet.user.id
            tweet_raw['user']['name'] = tweet.user.name
            tweet_raw['user']['description'] = tweet.user.description
            tweet_raw['user']['location'] = tweet.user.location
            tweet_raw['user']['followers_count'] = tweet.user.followers_count
            tweet_raw['user']['friends_count'] = tweet.user.friends_count
            analisis = TextBlob(tweet_raw['text'])
            try:
                sentiment = analisis.translate(to="en").sentiment
            except: 
                sentiment = analisis.sentiment
            tweet_raw['sentiment_polarity'] = sentiment.polarity
            tweet_raw['sentiment_subjectivity'] = sentiment.subjectivity
            logging.info(tweet_raw)
            tweets.append(tweet_raw)
        except tweepy.TweepError as e:
            logging.error(e)
            continue
        except StopIteration:
            logging.error(StopIteration)
            continue
        except ValueError:
            logging.error(ValueError)
            continue
    return tweets

def stripNonAlphaNum(text):
    import re
    return " ".join(re.compile(r'\W+', re.UNICODE).split(text))