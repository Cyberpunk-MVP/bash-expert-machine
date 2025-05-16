#!/bin/bash

RESOURCE_GROUP="$1"
NSG_NAME="$2"

if ||; then
  echo "Usage: $0 <resource_group> <nsg_name>"
  exit 1
fi

echo "Listing inbound security rules for NSG '$NSG_NAME' in resource group '$RESOURCE_GROUP'..."
az network nsg rule list --resource-group "$RESOURCE_GROUP" --nsg-name "$NSG_NAME" --direction Inbound --output table

echo "\nListing outbound security rules for NSG '$NSG_NAME' in resource group '$RESOURCE_GROUP'..."
az network nsg rule list --resource-group "$RESOURCE_GROUP" --nsg-name "$NSG_NAME" --direction Outbound --output table

8. Automating the Deployment of Azure Virtual Networks and Subnets

#!/bin/bash

# Configuration
RESOURCE_GROUP="$1"
VNET_NAME="$2"
LOCATION="$3"
ADDRESS_PREFIX="$4"
SUBNETS="$5" # Comma-separated list of subnet_name:subnet_prefix

if ||||; then
  echo "Usage: $0 <resource_group> <vnet_name> <location> <address_prefix> <subnet1_name:subnet1_prefix,subnet2_name:subnet2_prefix,...>"
  exit 1
fi

echo "Creating virtual network '$VNET_NAME' in resource group '$RESOURCE_GROUP' at '$LOCATION' with address prefix '$ADDRESS_PREFIX'..."
az network vnet create --resource-group "$RESOURCE_GROUP" --name "$VNET_NAME" --location "$LOCATION" --address-prefixes "$ADDRESS_PREFIX"

if [ $? -eq 0 ]; then
  echo "Virtual network '$VNET_NAME' created successfully."
  IFS=',' read -ra SUBNET_ARRAY <<< "$SUBNETS"
  for subnet_config in "${SUBNET_ARRAY[@]}"; do
    IFS=':' read -r SUBNET_NAME SUBNET_PREFIX <<< "$subnet_config"
    echo "Creating subnet '$SUBNET_NAME' with prefix '$SUBNET_PREFIX'..."
    az network vnet subnet create --resource-group "$RESOURCE_GROUP" --vnet-name "$VNET_NAME" --name "$SUBNET_NAME" --address-prefixes "$SUBNET_PREFIX"
    if [ $? -eq 0 ]; then
      echo "Subnet '$SUBNET_NAME' created successfully."
    else
      echo "Failed to create subnet '$SUBNET_NAME'."
    fi
  done
else
  echo "Failed to create virtual network '$VNET_NAME'."
fi
