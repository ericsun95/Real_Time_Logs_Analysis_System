# coding=UTF-8
# coding to generate logs
# crontab -e
# */1 * * * * sh /Users/eric_sun/IdeaProjects/SparkProject/log_generator.sh

import random
import time

url_paths = [
    "class/112.html",
    "class/130.html",
    "class/131.html",
    "class/145.html",
    "class/128.html",
    "class/146.html",
    "learn/821",
    "course/list"
]

ip_slices = [132, 156, 124, 10, 29, 167, 143, 187, 30, 46, 55, 63, 72, 87, 98]
http_referrers = [
    "http://www.baidu.com/s?wd={query}",
    "http://www.sogou.com/we?query=={query}",
    "http://cn.bing.com/search?q={query}",
    "http://search.yahoo.com/search?p={query}"
]

search_keywords = [
    "Spark SQL",
    "Hadoop",
    "Storm",
    "Spark Streaming",
    "Kafka",
    "Python"
]

match_dict = {
    "class/112.html" : "Spark SQL",
    "class/128.html" : "Kafka",
    "class/130.html" : "Hadoop",
    "class/131.html" : "Storm",
    "class/145.html" : "Spark Streaming",
    "class/146.html" : "Python",
    "learn/821" : "learnMaterials",
    "course/list" : "courseList"
}

status_codes = ["200", "404", "500"]
def sample_url():
    return random.sample(url_paths, 1)[0]

def sample_ip():
    slice = random.sample(ip_slices, 4)
    return ".".join([str(item) for item in slice])

def sample_referer(query_str):
    if random.uniform(0, 1) > 0.2:
        return "-"
    refer_str = random.sample(http_referrers, 1)
    # query_str = random.sample(search_keywords, 1)
    return refer_str[0].format(query=query_str)

def sample_status_code():
    return random.sample(status_codes, 1)[0]
def generate_log(count = 10):
    time_str = time.strftime("%Y-%m-%d %H:%M:%S", time.localtime())
    f = open("/Users/eric_sun/Documents/GitHub/Real_Time_Logs_Analysis_System/Data/access.log", "w+")
    while count >= 1:
        url_path = sample_url()
        query_str = match_dict[url_path]
        query_log = "{ip}\t{local_time}\t\"GET /{url} HTTP/1.1\"\t{status_code}\t{refer}" \
        .format(ip=sample_ip(), \
            local_time= time_str, \
            url=url_path, \
            status_code=sample_status_code(), \
            refer=sample_referer(query_str))
        print(query_log)
        f.write(query_log + "\n")
        count -= 1




if __name__ == "__main__":
    generate_log(20)
