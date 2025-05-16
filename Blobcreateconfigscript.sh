#!/bin/bash

# Configuration
RESOURCE_GROUP="$1"
STORAGE_ACCOUNT_NAME="$2"
LOCATION="$3"
CONTAINER_NAME="$4"
ACCESS_LEVEL="$5" # Private, Blob, Container

if |||||; then
  echo "Usage: $0 <resource_group> <storage_account_name> <location> <container_name> <private|blob|container>"
  exit 1
fi

echo "Creating storage account '$STORAGE_ACCOUNT_NAME' in resource group '$RESOURCE_GROUP' at '$LOCATION'..."
az storage account create --resource-group "$RESOURCE_GROUP" --name "$STORAGE_ACCOUNT_NAME" --location "$LOCATION" --sku Standard_LRS

if [ $? -eq 0 ]; then
  echo "Storage account '$STORAGE_ACCOUNT_NAME' created successfully."
  echo "Creating container '$CONTAINER_NAME' with access level '$ACCESS_LEVEL'..."
  az storage container create --account-name "$STORAGE_ACCOUNT_NAME" --name "$CONTAINER_NAME" --public-access "$ACCESS_LEVEL"
  if [ $? -eq 0 ]; then
    echo "Container '$CONTAINER_NAME' created successfully."
  else
    echo "Failed to create container '$CONTAINER_NAME'."
  fi
else
  echo "Failed to create storage account '$STORAGE_ACCOUNT_NAME'."
fi
