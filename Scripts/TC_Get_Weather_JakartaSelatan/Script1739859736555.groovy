import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import groovy.json.JsonSlurper

// Kirim request ke API
def response = WS.sendRequest(findTestObject('Object Repository/Get_Weather_JakartaSelatan'))

// Cek Status Code
WS.verifyResponseStatusCode(response, 200)

// Delay untuk menghindari rate limit (free tier)
Thread.sleep(1000) 

// Parse JSON Response
def jsonResponse = new JsonSlurper().parseText(response.getResponseBodyContent())

// Debugging: Cetak response API
println "[DEBUG] API Response: ${response.getResponseBodyContent()}"

// Validasi nama kota/kecamatan harus di Jakarta Selatan
def validAreas = ["Jakarta Selatan", "Jakarta", "Jagakarsa", "Pasar Minggu", "Kebayoran Baru", 
                  "Kebayoran Lama", "Pancoran", "Tebet", "Mampang Prapatan", "Cilandak", 
                  "Pesanggrahan", "Setiabudi"]

assert validAreas.any { area -> jsonResponse.city.name.toLowerCase().contains(area.toLowerCase()) } : 
    "ERROR: Expected Jakarta Selatan area, but got '${jsonResponse.city.name}'"

// Validasi lat & lon harus dalam rentang Jakarta Selatan
assert jsonResponse.city.coord.lat >= -6.3 && jsonResponse.city.coord.lat <= -6.2 : 
    "ERROR: Latitude out of range. Got '${jsonResponse.city.coord.lat}'"

assert jsonResponse.city.coord.lon >= 106.75 && jsonResponse.city.coord.lon <= 106.85 : 
    "ERROR: Longitude out of range. Got '${jsonResponse.city.coord.lon}'"

// Pastikan ada data cuaca
assert jsonResponse.list.size() > 0 : "ERROR: No weather data found"

// Validasi struktur JSON
assert jsonResponse.list[0].main.temp instanceof Number
assert jsonResponse.list[0].main.pressure instanceof Number
assert jsonResponse.list[0].main.humidity instanceof Number
assert jsonResponse.list[0].weather[0].main instanceof String
assert jsonResponse.list[0].weather[0].description instanceof String

println "SUCCESS: Weather API Test Passed!"
