import requests
import shutil
import PIL.Image as image

API_KEY = '8zzzh3gEngeZB+qOfBaSuQ==skyQK8uBdjCI8eIJ'

category = 'technology'
api_url = f'https://api.api-ninjas.com/v1/randomimage?category={category}'
response = requests.get(api_url, headers={'X-Api-Key': API_KEY, 'Accept': 'image/jpg'}, stream=True)
if response.status_code == requests.codes.ok:
	with open('docs/data_generating/img.jpg', 'wb') as out_file:
		# with out_file as 
		# print(out_file)
		# print(out_file)
		# img = image.frombuffer('L', (4,4), out_file)
		# image.open(img)
		shutil.copyfileobj(response.raw, out_file)
else:
	print("Error:", response.status_code, response.text)