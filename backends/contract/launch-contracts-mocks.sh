#!/bin/sh
# export SOAPUI_HOME_FOLDER="/home/cailleteau/SmartBear/SoapUI-5.4.0"
# export SOAPUI_PROJECT="/media/Donnees/Users/Laurent/blog/fivecents-microservices-demo/backends/contract/soapui-mocks/contract-soapui-project.xml"
# curl http://localhost:8080/0/contracts

echo SOAPUI_HOME_FOLDER : $SOAPUI_HOME_FOLDER
echo SOAPUI_PROJECT : $SOAPUI_PROJECT

cd $SOAPUI_HOME_FOLDER/bin
./mockservicerunner.sh -m "REST MockService" "$SOAPUI_PROJECT"


