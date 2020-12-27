# Minikube Kubernetes Installation for local Tests

To run some kubernetes or helm tests on your local machine, you can install Minikube. It installs a K8s with one node encapsulated in virtualbox.

## Execute the following commands
```bash
#!/bin/bash
sudo apt install virtualbox virtualbox-ext-pack
wget https://storage.googleapis.com/minikube/releases/latest/minikube-linux-amd64
sudo chmod 755 /usr/local/bin/minikube
minikube version
curl -LO https://storage.googleapis.com/kubernetes-release/release/`curl -s https://storage.googleapis.com/kubernetes-release/release/stable.txt`/bin/linux/amd64/kubectl
chmod +x ./kubectl
sudo mv ./kubectl /usr/local/bin/kubectl
kubectl version -o json
sudo apt install conntrack
sudo minikube start --vm-driver=none
```
## Check Installation

Run `sudo kubectl get all --all-namespaces` to check if kubectl command is running properly and cluster setup was successful.

## It should look like this:

```bash
anett@anett-laptop ~/git/lj-projectbuilder-github/lj-projectbuilder $ curl -LO https://storage.googleapis.com/kubernetes-release/release/`curl -s https://storage.googleapis.com/kubernetes-release/release/stable.txt`/bin/linux/amd64/kubectl
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
100 38.3M  100 38.3M    0     0  13.7M      0  0:00:02  0:00:02 --:--:-- 13.7M
anett@anett-laptop ~/git/lj-projectbuilder-github/lj-projectbuilder $ sudo apt install virtualbox virtualbox-ext-pack
Reading package lists... Done
Building dependency tree       
Reading state information... Done
The following additional packages will be installed:
  libgsoap-2.8.91 libvncserver1 virtualbox-dkms virtualbox-qt
Suggested packages:
  vde2 virtualbox-guest-additions-iso
The following NEW packages will be installed
  libgsoap-2.8.91 libvncserver1 virtualbox virtualbox-dkms virtualbox-ext-pack virtualbox-qt
0 to upgrade, 6 to newly install, 0 to remove and 2 not to upgrade.
Need to get 44.1 MB of archives.
After this operation, 179 MB of additional disk space will be used.
Do you want to continue? [Y/n] Y
Get:1 http://ftp.hosteurope.de/mirror/archive.ubuntu.com focal-updates/multiverse amd64 virtualbox-dkms amd64 6.1.10-dfsg-1~ubuntu1.20.04.1 [687 kB]
Get:2 http://ftp.hosteurope.de/mirror/archive.ubuntu.com focal/universe amd64 libgsoap-2.8.91 amd64 2.8.91-2 [231 kB]
Get:3 http://ftp.hosteurope.de/mirror/archive.ubuntu.com focal-updates/main amd64 libvncserver1 amd64 0.9.12+dfsg-9ubuntu0.3 [119 kB]
Get:4 http://ftp.hosteurope.de/mirror/archive.ubuntu.com focal-updates/multiverse amd64 virtualbox amd64 6.1.10-dfsg-1~ubuntu1.20.04.1 [21.4 MB]
Get:5 http://ftp.hosteurope.de/mirror/archive.ubuntu.com focal-updates/multiverse amd64 virtualbox-ext-pack all 6.1.10-1~ubuntu1.20.04.1 [10.1 kB]
Get:6 http://ftp.hosteurope.de/mirror/archive.ubuntu.com focal-updates/multiverse amd64 virtualbox-qt amd64 6.1.10-dfsg-1~ubuntu1.20.04.1 [21.7 MB]
Fetched 44.1 MB in 6s (7,697 kB/s)        
Preconfiguring packages ...
Selecting previously unselected package virtualbox-dkms.
(Reading database ... 559672 files and directories currently installed.)
Preparing to unpack .../0-virtualbox-dkms_6.1.10-dfsg-1~ubuntu1.20.04.1_amd64.deb ...
Unpacking virtualbox-dkms (6.1.10-dfsg-1~ubuntu1.20.04.1) ...
Selecting previously unselected package libgsoap-2.8.91:amd64.
Preparing to unpack .../1-libgsoap-2.8.91_2.8.91-2_amd64.deb ...
Unpacking libgsoap-2.8.91:amd64 (2.8.91-2) ...
Selecting previously unselected package libvncserver1:amd64.
Preparing to unpack .../2-libvncserver1_0.9.12+dfsg-9ubuntu0.3_amd64.deb ...
Unpacking libvncserver1:amd64 (0.9.12+dfsg-9ubuntu0.3) ...
Selecting previously unselected package virtualbox.
Preparing to unpack .../3-virtualbox_6.1.10-dfsg-1~ubuntu1.20.04.1_amd64.deb ...
Unpacking virtualbox (6.1.10-dfsg-1~ubuntu1.20.04.1) ...
Selecting previously unselected package virtualbox-ext-pack.
Preparing to unpack .../4-virtualbox-ext-pack_6.1.10-1~ubuntu1.20.04.1_all.deb ...
License has already been accepted.
Unpacking virtualbox-ext-pack (6.1.10-1~ubuntu1.20.04.1) ...
Selecting previously unselected package virtualbox-qt.
Preparing to unpack .../5-virtualbox-qt_6.1.10-dfsg-1~ubuntu1.20.04.1_amd64.deb ...
Unpacking virtualbox-qt (6.1.10-dfsg-1~ubuntu1.20.04.1) ...
Setting up libvncserver1:amd64 (0.9.12+dfsg-9ubuntu0.3) ...
Setting up libgsoap-2.8.91:amd64 (2.8.91-2) ...
Setting up virtualbox-dkms (6.1.10-dfsg-1~ubuntu1.20.04.1) ...
Loading new virtualbox-6.1.10 DKMS files...
Building for 5.4.0-58-generic
Building initial module for 5.4.0-58-generic
Secure Boot not enabled on this system.
Done.

vboxdrv.ko:
Running module version sanity check.
 - Original module
   - No original module exists within this kernel
 - Installation
   - Installing to /lib/modules/5.4.0-58-generic/updates/

vboxnetadp.ko:
Running module version sanity check.
 - Original module
   - No original module exists within this kernel
 - Installation
   - Installing to /lib/modules/5.4.0-58-generic/updates/

vboxnetflt.ko:
Running module version sanity check.
 - Original module
   - No original module exists within this kernel
 - Installation
   - Installing to /lib/modules/5.4.0-58-generic/updates/

depmod...

DKMS: install completed.
Setting up virtualbox (6.1.10-dfsg-1~ubuntu1.20.04.1) ...
Created symlink /etc/systemd/system/multi-user.target.wants/vboxweb.service → /lib/systemd/system/vboxweb.service.
Job for vboxweb.service failed because the service did not take the steps required by its unit configuration.
See "systemctl status vboxweb.service" and "journalctl -xe" for details.
Setting up virtualbox-ext-pack (6.1.10-1~ubuntu1.20.04.1) ...
removing old virtualbox extension packs
virtualbox-ext-pack: downloading: https://download.virtualbox.org/virtualbox/6.1.10/Oracle_VM_VirtualBox_Extension_Pack-6.1.10.vbox-extpack
The file will be downloaded into /usr/share/virtualbox-ext-pack
License accepted.
0%...10%...20%...30%...40%...50%...60%...70%...80%...90%...100%
Successfully installed "Oracle VM VirtualBox Extension Pack".
Setting up virtualbox-qt (6.1.10-dfsg-1~ubuntu1.20.04.1) ...
Processing triggers for desktop-file-utils (0.24+linuxmint1) ...
Processing triggers for mime-support (3.64ubuntu1) ...
Processing triggers for hicolor-icon-theme (0.17-2) ...
Processing triggers for gnome-menus (3.36.0-1ubuntu1) ...
Processing triggers for libc-bin (2.31-0ubuntu9.1) ...
Processing triggers for systemd (245.4-4ubuntu3.3) ...
Processing triggers for man-db (2.9.1-1) ...
Processing triggers for ureadahead (0.100.0-21) ...
Processing triggers for shared-mime-info (1.15-1) ...
anett@anett-laptop ~/git/lj-projectbuilder-github/lj-projectbuilder $ wget https://storage.googleapis.com/minikube/releases/latest/minikube-linux-amd64
--2020-12-27 21:25:06--  https://storage.googleapis.com/minikube/releases/latest/minikube-linux-amd64
Resolving storage.googleapis.com (storage.googleapis.com)... 172.217.18.112, 216.58.210.16, 216.58.212.176, ...
Connecting to storage.googleapis.com (storage.googleapis.com)|172.217.18.112|:443... connected.
HTTP request sent, awaiting response... 200 OK
Length: 57925077 (55M) [application/octet-stream]
Saving to: ‘minikube-linux-amd64.1’

minikube-linux-amd64.1                                      100%[=========================================================================================================================================>]  55.24M  16.2MB/s    in 3.5s    

2020-12-27 21:25:10 (15.6 MB/s) - ‘minikube-linux-amd64.1’ saved [57925077/57925077]

anett@anett-laptop ~/git/lj-projectbuilder-github/lj-projectbuilder $ sudo cp minikube-linux-amd64 /usr/local/bin/minikube
anett@anett-laptop ~/git/lj-projectbuilder-github/lj-projectbuilder $ sudo chmod 755 /usr/local/bin/minikube
anett@anett-laptop ~/git/lj-projectbuilder-github/lj-projectbuilder $ minikube version
minikube version: v1.16.0
commit: 9f1e482427589ff8451c4723b6ba53bb9742fbb1
anett@anett-laptop ~/git/lj-projectbuilder-github/lj-projectbuilder $ curl -LO https://storage.googleapis.com/kubernetes-release/release/`curl -s https://storage.googleapis.com/kubernetes-release/release/stable.txt`/bin/linux/amd64/kubectl
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
100 38.3M  100 38.3M    0     0  13.3M      0  0:00:02  0:00:02 --:--:-- 13.3M
anett@anett-laptop ~/git/lj-projectbuilder-github/lj-projectbuilder $ chmod +x ./kubectl
anett@anett-laptop ~/git/lj-projectbuilder-github/lj-projectbuilder $ sudo mv ./kubectl /usr/local/bin/kubectl
anett@anett-laptop ~/git/lj-projectbuilder-github/lj-projectbuilder $ kubectl version -o json
{
  "clientVersion": {
    "major": "1",
    "minor": "20",
    "gitVersion": "v1.20.1",
    "gitCommit": "c4d752765b3bbac2237bf87cf0b1c2e307844666",
    "gitTreeState": "clean",
    "buildDate": "2020-12-18T12:09:25Z",
    "goVersion": "go1.15.5",
    "compiler": "gc",
    "platform": "linux/amd64"
  }
}
The connection to the server localhost:8080 was refused - did you specify the right host or port?
anett@anett-laptop ~/git/lj-projectbuilder-github/lj-projectbuilder $ minikube start^C
anett@anett-laptop ~/git/lj-projectbuilder-github/lj-projectbuilder $ minikube start —-vm-driver=none
😄  minikube v1.16.0 on Linuxmint 20
✨  Automatically selected the virtualbox driver
💿  Downloading VM boot image ...
    > minikube-v1.16.0.iso.sha256: 65 B / 65 B [-------------] 100.00% ? p/s 0s
    > minikube-v1.16.0.iso: 212.62 MiB / 212.62 MiB [ 100.00% 15.40 MiB p/s 14s
👍  Starting control plane node minikube in cluster minikube
💾  Downloading Kubernetes v1.20.0 preload ...
    > preloaded-images-k8s-v8-v1....: 491.00 MiB / 491.00 MiB  100.00% 15.85 Mi
🔥  Creating virtualbox VM (CPUs=2, Memory=2200MB, Disk=20000MB) ...
🔥  Deleting "minikube" in virtualbox ...
🤦  StartHost failed, but will try again: creating host: create: creating: Unable to start the VM: /usr/bin/VBoxManage startvm minikube --type headless failed:
VBoxManage: error: VT-x is disabled in the BIOS for all CPU modes (VERR_VMX_MSR_ALL_VMX_DISABLED)
VBoxManage: error: Details: code NS_ERROR_FAILURE (0x80004005), component ConsoleWrap, interface IConsole

Details: 00:00:00.237980 Power up failed (vrc=VERR_VMX_MSR_ALL_VMX_DISABLED, rc=NS_ERROR_FAILURE (0X80004005))
🔥  Creating virtualbox VM (CPUs=2, Memory=2200MB, Disk=20000MB) ...
😿  Failed to start virtualbox VM. Running "minikube delete" may fix it: creating host: create: creating: Unable to start the VM: /usr/bin/VBoxManage startvm minikube --type headless failed:
VBoxManage: error: VT-x is disabled in the BIOS for all CPU modes (VERR_VMX_MSR_ALL_VMX_DISABLED)
VBoxManage: error: Details: code NS_ERROR_FAILURE (0x80004005), component ConsoleWrap, interface IConsole

Details: 00:00:00.212125 Power up failed (vrc=VERR_VMX_MSR_ALL_VMX_DISABLED, rc=NS_ERROR_FAILURE (0X80004005))
❗  Startup with virtualbox driver failed, trying with alternate driver none: Failed to start host: creating host: create: creating: Unable to start the VM: /usr/bin/VBoxManage startvm minikube --type headless failed:
VBoxManage: error: VT-x is disabled in the BIOS for all CPU modes (VERR_VMX_MSR_ALL_VMX_DISABLED)
VBoxManage: error: Details: code NS_ERROR_FAILURE (0x80004005), component ConsoleWrap, interface IConsole

Details: 00:00:00.212125 Power up failed (vrc=VERR_VMX_MSR_ALL_VMX_DISABLED, rc=NS_ERROR_FAILURE (0X80004005))
🔥  Deleting "minikube" in virtualbox ...
💀  Removed all traces of the "minikube" cluster.

❌  Exiting due to GUEST_MISSING_CONNTRACK: Sorry, Kubernetes 1.20.0 requires conntrack to be installed in root's path

anett@anett-laptop ~/git/lj-projectbuilder-github/lj-projectbuilder $ minikube start --vm-driver=none
😄  minikube v1.16.0 on Linuxmint 20
✨  Using the none driver based on user configuration

❌  Exiting due to GUEST_MISSING_CONNTRACK: Sorry, Kubernetes 1.20.0 requires conntrack to be installed in root's path

anett@anett-laptop ~/git/lj-projectbuilder-github/lj-projectbuilder $ sudo apt install conntrack
Reading package lists... Done
Building dependency tree       
Reading state information... Done
Suggested packages:
  nftables
The following NEW packages will be installed
  conntrack
0 to upgrade, 1 to newly install, 0 to remove and 2 not to upgrade.
Need to get 30.3 kB of archives.
After this operation, 104 kB of additional disk space will be used.
Get:1 http://ftp.hosteurope.de/mirror/archive.ubuntu.com focal/main amd64 conntrack amd64 1:1.4.5-2 [30.3 kB]
Fetched 30.3 kB in 0s (191 kB/s)     
Selecting previously unselected package conntrack.
(Reading database ... 560330 files and directories currently installed.)
Preparing to unpack .../conntrack_1%3a1.4.5-2_amd64.deb ...
Unpacking conntrack (1:1.4.5-2) ...
Setting up conntrack (1:1.4.5-2) ...
Processing triggers for man-db (2.9.1-1) ...
anett@anett-laptop ~/git/lj-projectbuilder-github/lj-projectbuilder $ sudo minikube start --vm-driver=none
😄  minikube v1.16.0 on Linuxmint 20
✨  Using the none driver based on user configuration
👍  Starting control plane node minikube in cluster minikube
🤹  Running on localhost (CPUs=4, Memory=7726MB, Disk=97038MB) ...
ℹ️  OS release is Linux Mint 20
🐳  Preparing Kubernetes v1.20.0 on Docker 20.10.1 ...
    ▪ kubelet.resolv-conf=/run/systemd/resolve/resolv.conf
    > kubelet.sha256: 64 B / 64 B [--------------------------] 100.00% ? p/s 0s
    > kubectl.sha256: 64 B / 64 B [--------------------------] 100.00% ? p/s 0s
    > kubeadm.sha256: 64 B / 64 B [--------------------------] 100.00% ? p/s 0s
    > kubeadm: 37.40 MiB / 37.40 MiB [----------------] 100.00% 5.88 MiB p/s 7s
    > kubectl: 38.37 MiB / 38.37 MiB [----------------] 100.00% 5.53 MiB p/s 7s
    > kubelet: 108.69 MiB / 108.69 MiB [-------------] 100.00% 9.28 MiB p/s 12s
    ▪ Generating certificates and keys ...
    ▪ Booting up control plane ...
    ▪ Configuring RBAC rules ...
🤹  Configuring local host environment ...

❗  The 'none' driver is designed for experts who need to integrate with an existing VM
💡  Most users should use the newer 'docker' driver instead, which does not require root!
📘  For more information, see: https://minikube.sigs.k8s.io/docs/reference/drivers/none/

❗  kubectl and minikube configuration will be stored in /root
❗  To use kubectl or minikube commands as your own user, you may need to relocate them. For example, to overwrite your own settings, run:

    ▪ sudo mv /root/.kube /root/.minikube $HOME
    ▪ sudo chown -R $USER $HOME/.kube $HOME/.minikube

💡  This can also be done automatically by setting the env var CHANGE_MINIKUBE_NONE_USER=true
🔎  Verifying Kubernetes components...
🌟  Enabled addons: storage-provisioner, default-storageclass
🏄  Done! kubectl is now configured to use "minikube" cluster and "default" namespace by default
anett@anett-laptop ~/git/lj-projectbuilder-github/lj-projectbuilder $ ^C
anett@anett-laptop ~/git/lj-projectbuilder-github/lj-projectbuilder $ ^C
anett@anett-laptop ~/git/lj-projectbuilder-github/lj-projectbuilder $ ^C
anett@anett-laptop ~/git/lj-projectbuilder-github/lj-projectbuilder $ kubectl get all --all-namespaces
The connection to the server localhost:8080 was refused - did you specify the right host or port?
anett@anett-laptop ~/git/lj-projectbuilder-github/lj-projectbuilder $ kubectl get all
The connection to the server localhost:8080 was refused - did you specify the right host or port?
anett@anett-laptop ~/git/lj-projectbuilder-github/lj-projectbuilder $ sudo kubectl get all
NAME                 TYPE        CLUSTER-IP   EXTERNAL-IP   PORT(S)   AGE
service/kubernetes   ClusterIP   10.96.0.1    <none>        443/TCP   7m26s
anett@anett-laptop ~/git/lj-projectbuilder-github/lj-projectbuilder $ sudo kubectl get all --all-namespaces
NAMESPACE     NAME                                       READY   STATUS    RESTARTS   AGE
kube-system   pod/coredns-74ff55c5b-2ph84                1/1     Running   0          7m16s
kube-system   pod/etcd-anett-laptop                      1/1     Running   0          7m24s
kube-system   pod/kube-apiserver-anett-laptop            1/1     Running   0          7m24s
kube-system   pod/kube-controller-manager-anett-laptop   1/1     Running   0          7m24s
kube-system   pod/kube-proxy-vp7x4                       1/1     Running   0          7m16s
kube-system   pod/kube-scheduler-anett-laptop            1/1     Running   0          7m24s
kube-system   pod/storage-provisioner                    1/1     Running   0          7m28s

NAMESPACE     NAME                 TYPE        CLUSTER-IP   EXTERNAL-IP   PORT(S)                  AGE
default       service/kubernetes   ClusterIP   10.96.0.1    <none>        443/TCP                  7m33s
kube-system   service/kube-dns     ClusterIP   10.96.0.10   <none>        53/UDP,53/TCP,9153/TCP   7m31s

NAMESPACE     NAME                        DESIRED   CURRENT   READY   UP-TO-DATE   AVAILABLE   NODE SELECTOR            AGE
kube-system   daemonset.apps/kube-proxy   1         1         1       1            1           kubernetes.io/os=linux   7m31s

NAMESPACE     NAME                      READY   UP-TO-DATE   AVAILABLE   AGE
kube-system   deployment.apps/coredns   1/1     1            1           7m31s

NAMESPACE     NAME                                DESIRED   CURRENT   READY   AGE
kube-system   replicaset.apps/coredns-74ff55c5b   1         1         1       7m16s
anett@anett-laptop ~/git/lj-projectbuilder-github/lj-projectbuilder $


```
