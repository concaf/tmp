val findEvenNumbers = (numbers: List[Int]) => {
  numbers.filter((e => e % 2 == 0))
}

findEvenNumbers(List(1,2,3,4))