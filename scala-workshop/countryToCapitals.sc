val countryToCapitals = (countryList: List[String]) => {
  countryList.map(c => {
    if ( c == "India") "Delhi"
    else if (c == "UK") "London"
  })
}

countryToCapitals(List("India", "Bhutan", "UK"))