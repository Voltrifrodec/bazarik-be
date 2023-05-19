# Module Imports
import mariadb
import sys

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

cursor.execute('''
	SELECT id, original_size_bytes, size_bytes FROM image
''')

for (id, original_size_bytes, size_bytes) in cursor:
    print(f'id: {id}, original_size_bytes: {original_size_bytes:6}, size_bytes: {size_bytes:6}, diff: {((original_size_bytes - size_bytes) / 1000):6}kB')