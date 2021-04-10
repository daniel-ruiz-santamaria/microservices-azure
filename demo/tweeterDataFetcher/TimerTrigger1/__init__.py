import datetime
import logging

import azure.functions as func
from . import config as conf
from . import tweeter_handler
from . import publisher_eventhub_handler as peh
import json
import asyncio
import nest_asyncio
nest_asyncio.apply()

def main(mytimer: func.TimerRequest) -> None:
    utc_timestamp = datetime.datetime.utcnow()
    politic_search = tweeter_handler.get_politic(utc_timestamp.minute)
    config = conf.get_tweeter_config()
    tweets = tweeter_handler.get_tweets(config,politic_search)
    peh.send_tweets_event_hub(tweets)
    logging.info('Completed send data')
    logging.info('Python timer trigger function ran at %s', utc_timestamp)
