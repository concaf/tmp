val winningCandidate = (votes: List[Int]) => {
  votes.reduce((winner, current) => {
    if (current > winner) current
    else winner
  })
}

winningCandidate(List(20,40,100))