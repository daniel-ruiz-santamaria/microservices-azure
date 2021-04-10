from azure.appconfiguration import AzureAppConfigurationClient

connection_str = "Endpoint=https://config-demo-azure.azconfig.io;Id=/sWh-l9-s0:n06YX28m2mvHmdknfJdx;Secret=lVepuj2LbqdRjl5dHDXc1U/M7Q0QkXlt71Qhdq6SxSE="
client = AzureAppConfigurationClient.from_connection_string(connection_str)

def get_tweeter_config():
    tweeter_conf = {}
    tweeter_conf['access_token'] = client.get_configuration_setting(
        key="tweeter_access_token"
    ).value
    tweeter_conf['access_token_secret'] = client.get_configuration_setting(
        key="tweeter_access_token_secret"
    ).value
    tweeter_conf['consumer_key'] = client.get_configuration_setting(
        key="tweeter_consumer_key"
    ).value
    tweeter_conf['consumer_secret'] = client.get_configuration_setting(
        key="tweeter_consumer_secret"
    ).value
    tweeter_conf['tweets_number'] = client.get_configuration_setting(
        key="tweets_number"
    ).value
    return tweeter_conf

def get_eventhub_config():
    eventhub_conf = {}
    eventhub_conf['tweeter-name'] = client.get_configuration_setting(
        key="eventhub-tweeter-name"
    ).value
    eventhub_conf['connection_string'] = client.get_configuration_setting(
        key="evet_hub_connection_string"
    ).value
    return eventhub_conf