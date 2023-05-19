# Module Imports
import mariadb
import sys

import uuid

from itertools import permutations

c = permutations([{123, 345}, {213, 123}, {654, 231, 111}], 2)

print([x for x in c])

'''


'''

# Connect to MariaDB Platform
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

# Get Cursor
cursor = connection.cursor()

# Get all image ids
def getImageIds():
	try:
		cursor.execute('SELECT id FROM image')
		return [id[0] for (id) in cursor]
	except mariadb.Error as e:
		print(f'Error: {e}')

def getRandomUuid() -> str:
	return str(uuid.uuid4())

class Advert:
	# TODO: Add constructor parameters
	def __init__(self) -> None:
		self.id_advert: str
		self.date_added: str
		self.date_modified: str
		self.description: str
		self.fixed_price: bool
		self.keywords: str
		self.name: str
		self.price_eur: int
		
		self.id_category: int
		self.id_subcategory: int
		self.id_subsubcategory: int

		self.id_contact: int
		self.id_currency: int
		self.id_district: int

		self.id_image: int

		pass
	
	def generateQuery() -> str:
		# TODO: generate sql query
		pass

print(len(getRandomUuid()))

imageIdsSet = set(getImageIds())
print(imageIdsSet)

# for (id, original_size_bytes, size_bytes) in cursor:
# 	print(f'id: {id}, original_size_bytes: {original_size_bytes:6}, size_bytes: {size_bytes:6}, diff: {((original_size_bytes - size_bytes) / 1000):6}kB')

