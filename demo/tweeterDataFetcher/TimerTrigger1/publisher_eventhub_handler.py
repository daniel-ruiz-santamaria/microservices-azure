import asyncio

from azure.eventhub import EventHubProducerClient, EventData
from azure.eventhub.exceptions import EventHubError

import nest_asyncio
nest_asyncio.apply()
import logging

from . import config

import json

conf = config.get_eventhub_config()

producer = EventHubProducerClient.from_connection_string(
        conn_str=conf['connection_string'],
        eventhub_name=conf['tweeter-name']
    )

def send_tweets_event_hub(tweets):
    # Without specifying partition_id or partition_key
    # the events will be distributed to available partitions via round-robin.


    event_data_batch = producer.create_batch()
    for tweet in tweets:
        try:
            tweet_str = json.dumps(tweet)
            event_data_batch.add(EventData(tweet_str))
            logging.info('Send message: %s',tweet_str)
        except:
            continue
    producer.send_batch(event_data_batch)
    logging.info('Send message done')
