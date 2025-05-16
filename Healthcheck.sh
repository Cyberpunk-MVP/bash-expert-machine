#!/bin/bash

RESOURCE_GROUP="$1"
VM_NAME="$2"

if ||; then
  echo "Usage: $0 <resource_group> <vm_name>"
  exit 1
fi

echo "Checking status of VM '$VM_NAME' in resource group '$RESOURCE_GROUP'..."
VM_STATUS=$(az vm get-instance-view --resource-group "$RESOURCE_GROUP" --name "$VM_NAME" --query "statuses.displayStatus" --output tsv)

echo "Status: $VM_STATUS"
