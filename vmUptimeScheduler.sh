#!/bin/bash

# Configuration
RESOURCE_GROUP="$1"
VM_NAME="$2"
ACTION="$3" # "start" or "stop"

if ||; then
  echo "Usage: $0 <resource_group> <vm_name> <start|stop>"
  exit 1
fi

TIMESTAMP=$(date +"%Y-%m-%d %H:%M:%S")

if; then
  echo "$TIMESTAMP - Attempting to start VM '$VM_NAME' in resource group '$RESOURCE_GROUP'..."
  az vm start --resource-group "$RESOURCE_GROUP" --name "$VM_NAME"
  if [ $? -eq 0 ]; then
    echo "$TIMESTAMP - VM '$VM_NAME' started successfully."
  else
    echo "$TIMESTAMP - Failed to start VM '$VM_NAME'."
  fi
elif; then
  echo "$TIMESTAMP - Attempting to stop VM '$VM_NAME' in resource group '$RESOURCE_GROUP'..."
  az vm stop --resource-group "$RESOURCE_GROUP" --name "$VM_NAME" --no-wait
  if [ $? -eq 0 ]; then
    echo "$TIMESTAMP - VM '$VM_NAME' stop initiated successfully."
  else
    echo "$TIMESTAMP - Failed to initiate stop for VM '$VM_NAME'."
  fi
else
  echo "$TIMESTAMP - Invalid action. Use 'start' or 'stop'."
fi
