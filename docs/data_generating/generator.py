import mariadb
import sys
import uuid
import datetime
import random
import lorem
import requests
import json

'''
1. napojí sa python na db:
	otestuje sa pripojenie!

2. načítajú sa id pre:
	image
	category
	subcategory
	subsubcategory
	contact
	currency (=1)
	district

3. vyber náhodné hodnoty pre:
	image, ..., district

4. vytvor classu advert
5. pošli query na mariadb cez getQuery() a cursor.query

'''

try:
	connection = mariadb.connect(
		user="root",
		password="root",
		host="localhost",
		port=3306,
		database="bazarik"
	)
except mariadb.Error as e:
	print(f"Error connecting to MariaDB Platform: {e}")
	sys.exit(1)

cursor = connection.cursor()

def getRightCategoryCombination():
	try:
		cursor.execute('SELECT id FROM image')
		return [id[0] for (id) in cursor]
	except mariadb.Error as e:
		print(f'Error: {e}')

# Get all image ids
def getImageIds():
	try:
		cursor.execute('SELECT id FROM image')
		return [id[0] for (id) in cursor]
	except mariadb.Error as e:
		print(f'Error: {e}')

def getCategoryIds():
	try:
		cursor.execute('SELECT id_category FROM category')
		return [id[0] for (id) in cursor]
	except mariadb.Error as e:
		print(f'Error: {e}')

def getSubcategoryIds():
	try:
		cursor.execute('SELECT id_subcategory FROM subcategory')
		return [id[0] for (id) in cursor]
	except mariadb.Error as e:
		print(f'Error: {e}')

def getSubsubcategoryIds():
	try:
		cursor.execute('SELECT id_subsubcategory FROM subsubcategory')
		return [id[0] for (id) in cursor]
	except mariadb.Error as e:
		print(f'Error: {e}')

def getContactIds():
	try:
		cursor.execute('SELECT id FROM contact')
		return [id[0] for (id) in cursor]
	except mariadb.Error as e:
		print(f'Error: {e}')

def getContactEmails():
	try:
		cursor.execute('SELECT email FROM contact')
		return [email[0] for (email) in cursor]
	except mariadb.Error as e:
		print(f'Error: {e}')

def getCurrencyIds():
	return 1

def getDistrictIds():
	try:
		cursor.execute('SELECT id_district FROM district')
		return [id[0] for (id) in cursor]
	except mariadb.Error as e:
		print(f'Error: {e}')

def getRandomUuid() -> str:
	return str(uuid.uuid4())


class Advert:
	# TODO: Add constructor parameters
	def __init__(self,
	      id_advert,
		  date_added,
		  date_modified,
		  description,
		  fixed_price,
		  keywords,
		  name,
		  price_eur,
		  id_category,
		  id_subcategory,
		  id_subsubcategory,
		  id_contact,
		  id_currency,
		  id_district,
		  id_image) -> None:
		self.id_advert: str = id_advert
		self.date_added: str = date_added
		self.date_modified: str = date_modified
		self.description: str = description
		self.fixed_price: bool = fixed_price
		self.keywords: str =  keywords
		self.name: str = name
		self.price_eur: int = price_eur
		
		self.id_category: int = id_category
		self.id_subcategory: int = id_subcategory
		self.id_subsubcategory: int = id_subsubcategory

		self.id_contact: int = id_contact
		self.id_currency: int = id_currency
		self.id_district: int = id_district

		self.id_image: int = id_image

	def generateQuery(self) -> str:
		query = f'''
			INSERT INTO advert(id_advert,date_added,date_modified,description,fixed_price,keywords,name,price_eur,id_category,id_subcategory,id_subsubcategory,id_contact,id_currency,id_district,id_image)
			VALUES ({self.id_advert}, )
		'''
		# TODO: generate sql query
		return query;


if __name__ == '__main__':
	api_url = "http://localhost:8080/api/adverts"

	pocetInzeratov = 1_000_000

	categoryIds = getCategoryIds()
	subcategoryIds = getSubcategoryIds()
	subsubcategoryIds = getSubsubcategoryIds()
	emails = getContactEmails()
	contactIds = getContactIds()
	currencyIds = getCurrencyIds()
	districtIds = getDistrictIds()
	imageIds = getImageIds()

	for i in range(pocetInzeratov):
		id_advert = getRandomUuid()
		
		date_added = datetime.datetime.now()
		date_modified = date_added
		description = lorem.paragraph()
		fixed_price = random.getrandbits(1)
		keywords = ''
		name = lorem.sentence()
		price_eur = random.randrange(0, 10000)

		email = random.choice(emails)

		id_category = random.choice(categoryIds)
		id_subcategory = random.choice(subcategoryIds)
		id_subsubcategory = random.choice(subsubcategoryIds)
		
		id_contact = random.choice(contactIds)
		id_currency = currencyIds # Change to random.choice(currencyIds) when necessary
		id_district = random.choice(districtIds)
		id_image = random.choice(imageIds)

		""" Does not work right now
		inzerat = Advert(id_advert,
			date_added,
			date_modified,
			description,
			fixed_price,
			keywords,
			name,
			price_eur,

			id_category,
			id_subcategory,
			id_subsubcategory,
			id_contact,
			id_currency,
			id_district,
			id_image
		) """

		# print(inzerat)
		# query = inzerat.generateQuery()
		# cursor.execute(query)

		payload = json.dumps({
		  "name": name,
		  "description": description,
		  "keywords": keywords,
		  "priceEur": price_eur,
		  "fixedPrice": fixed_price,
		  "contactEmail": email,

		  "categoryId": id_category,
		  "subcategoryId": id_subcategory,
		  "subsubcategoryId": id_subsubcategory,

		  "districtId": id_district,

		  "imageId": id_image
		})

		headers = {
		  'Content-Type': 'application/json',
		  'Authorization': 'Bearer c013bca0-eebc-47ad-9aa2-cde3130f989a'
		}

		requests.request("POST", api_url, headers=headers, data=payload)
		print(f'Poslal som {i}-ty request')
