val averageSalary = (salaries: List[Double]) => {
  salaries.reduce((average, salary) => (average+salary)/2 )
}

averageSalary(List(20000, 30000, 100000))