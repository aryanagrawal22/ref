from bs4 import BeautifulSoup as bs
import requests

link='https://www.amazon.in/ask/questions/asin/B07DJHV6VZ/ref=ask_rp_reva_ql_hza'
page = requests.get(link)

soup = bs(page.content,'html.parser')
print(soup.prettify())

names = soup.find_all('span',class_='a-profile-name')
print(names)

cust_name = []
for i in range(0,len(names)):
    cust_name.append(names[i].get_text())
print(cust_name)