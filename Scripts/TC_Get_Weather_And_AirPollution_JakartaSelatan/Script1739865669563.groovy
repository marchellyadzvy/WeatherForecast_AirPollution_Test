import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import groovy.json.JsonSlurper

// Fungsi untuk menunda eksekusi guna menghindari rate limit
def delayRequest() {
    int delayTime = 1100 // Delay dalam milidetik (OpenWeather API free tier rate limit: 60 calls/min)
    Thread.sleep(delayTime)
}

// Mengambil data Weather Forecast Jakarta Selatan
def getWeatherForecast() {
    def response = WS.sendRequest(findTestObject('Object Repository/Get_Weather_JakartaSelatan'))
    WS.verifyResponseStatusCode(response, 200)
    delayRequest()
    return new JsonSlurper().parseText(response.getResponseBodyContent())
}

// Mengambil data Air Pollution Jakarta Selatan
def getAirPollution() {
    def response = WS.sendRequest(findTestObject('Object Repository/Get_AirPollution_JakartaSelatan'))
    WS.verifyResponseStatusCode(response, 200)
    delayRequest()
    return new JsonSlurper().parseText(response.getResponseBodyContent())
}

// Validasi response Weather Forecast
def validateWeatherResponse(def jsonResponse) {
    // List kecamatan di Jakarta Selatan
    def validKecamatan = [
        "kebayoran baru", "kebayoran lama", "pesanggrahan",
        "cilandak", "jagakarsa", "pasar minggu",
        "mampang prapatan", "tebet", "setiabudi"
    ]

    def cityName = jsonResponse.city?.name?.toLowerCase()?.trim()

    println "[DEBUG] City Name from API: '${cityName}'"
    println "[DEBUG] Valid Kecamatan List: ${validKecamatan}"

    assert validKecamatan.contains(cityName) : 
        "❌ ERROR: Expected Jakarta Selatan area, but got '${cityName}'"

    assert jsonResponse.list.size() > 0
    assert jsonResponse.list[0].main.temp instanceof Number
    assert jsonResponse.list[0].main.pressure instanceof Number
    assert jsonResponse.list[0].main.humidity instanceof Number
    assert jsonResponse.list[0].weather[0].main instanceof String
    assert jsonResponse.list[0].weather[0].description instanceof String
}

// Validasi response Air Pollution
def validateAirPollutionResponse(def jsonResponse) {
    assert jsonResponse.coord.lat >= -6.3 && jsonResponse.coord.lat <= -6.2
    assert jsonResponse.coord.lon >= 106.75 && jsonResponse.coord.lon <= 106.85
    assert jsonResponse.list[0].components.co instanceof Number
    assert jsonResponse.list[0].components.no2 instanceof Number
    assert jsonResponse.list[0].components.pm10 instanceof Number
    assert jsonResponse.list[0].components.pm2_5 instanceof Number
}

// Eksekusi dan validasi
println "[INFO] Running Weather Forecast Test"
def weatherData = getWeatherForecast()
validateWeatherResponse(weatherData)

println "[INFO] Running Air Pollution Test"
def pollutionData = getAirPollution()
validateAirPollutionResponse(pollutionData)

println "✅ All tests passed successfully!"
