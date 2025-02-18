import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import groovy.json.JsonSlurper

// Kirim request ke API
def response = WS.sendRequest(findTestObject('Object Repository/Get_AirPollution_JakartaSelatan'))

// Cek Status Code
WS.verifyResponseStatusCode(response, 200)

// Delay untuk menghindari rate limit
Thread.sleep(1000) 

// Parse JSON Response
def jsonResponse = new JsonSlurper().parseText(response.getResponseBodyContent())

// Debugging: Cetak response API 
println "[DEBUG] API Response: ${response.getResponseBodyContent()}"

// Validasi koordinat ->>> dalam rentang Jakarta Selatan
assert jsonResponse.coord.lat >= -6.3 && jsonResponse.coord.lat <= -6.2 : 
    "ERROR: Latitude out of range. Got '${jsonResponse.coord.lat}'"

assert jsonResponse.coord.lon >= 106.75 && jsonResponse.coord.lon <= 106.85 : 
    "ERROR: Longitude out of range. Got '${jsonResponse.coord.lon}'"

// data polusi udara
assert jsonResponse.list.size() > 0 : "ERROR: No air pollution data found"

// Validasi struktur JSON
assert jsonResponse.list[0].components.co instanceof Number
assert jsonResponse.list[0].components.no2 instanceof Number
assert jsonResponse.list[0].components.pm10 instanceof Number
assert jsonResponse.list[0].components.pm2_5 instanceof Number

println "SUCCESS: Air Pollution API Test Passed!"
