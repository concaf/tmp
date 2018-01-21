val sumOfInt = (numbers: List[Int]) => {
  numbers.reduce((a, n) => a + n)
}

sumOfInt(List(1,3,4,6))