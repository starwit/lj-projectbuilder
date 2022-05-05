## Running on Kubernetes

Go to helm/ljprojectbuilder
- copy values-template4private.yaml to values-private.yaml and add secret values
- execute e.g.:
  - for local cluster: `helm install ljprojectbuilder . -n ljprojectbuilder -f values.yaml -f values-private.yaml`
  - for hetzner cluster: `helm install ljprojectbuilder . -n ljprojectbuilder -f values.yaml -f values-hetzner.yaml`