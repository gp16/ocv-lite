# This file includes the scripts that the continuous integration server,
# magnum-ci, runs in order to build, run, and execute the test cases.

# before installation
sudo rm /etc/apt/sources.list.d/google.list
sudo apt-get update -y
sudo apt-get install python-software-properties -y
sudo add-apt-repository ppa:webupd8team/java -y
sudo apt-get update -y -y
sudo echo debconf shared/accepted-oracle-license-v1-1 select true | sudo debconf-set-selections
sudo echo debconf shared/accepted-oracle-license-v1-1 seen true | sudo debconf-set-selections
sudo apt-get install oracle-java8-installer oracle-java8-set-default -y
java -version
sudo apt-get install ant -y
ant compile

# dependency installation
cd ../
export JAVA_HOME=/usr/lib/jvm/java-8-oracle
sudo apt-get install git -y
sudo apt-get install cmake -y
git clone https://github.com/Itseez/opencv.git
cd opencv
git checkout 2.4.9
mkdir build
cd build
cmake ..
make -j8
cd ../../

# before test execution
cd ./ocvlite/
sudo apt-get install junit -y

# test suit commands
ant test -Djava.library.path=/home/magnum/opencv/build/lib || true
