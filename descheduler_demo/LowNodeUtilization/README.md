- `watch -n 0.1 'kubectl describe nodes | grep -A 2 -e "^\\s*CPU Requests"'`
- `kubectl drain <2 nodes>`
- `kubectl scale deployment wordpress --replicas 9`
- `kubectl uncordon <2 nodes>`
- `descheduler --kubeconfig $KUBECONFIG --policy-config-file policy.yaml -v 5`

