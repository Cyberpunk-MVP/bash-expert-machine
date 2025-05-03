
#!/bin/bash

# Configuration
RESOURCE_GROUP="$1"
VM_NAME="$2"
THRESHOLD_PERCENT="$3" # CPU utilization percentage threshold
NEW_SIZE="$4"

if ||||; then
  echo "Usage: $0 <resource_group> <vm_name> <cpu_threshold_percent> <new_vm_size>"
  exit 1
fi

TIMESTAMP=$(date +"%Y-%m-%d %H:%M:%S")

echo "$TIMESTAMP - Checking CPU utilization for VM '$VM_NAME' in resource group '$RESOURCE_GROUP'..."

CPU_UTIL=$(az monitor metrics list --resource "$(/subscriptions/$(az account show --query id --output tsv)/resourceGroups/$RESOURCE_GROUP/providers/Microsoft.Compute/virtualMachines/$VM_NAME)" --metric "Percentage CPU" --interval PT1M --count 1 --output tsv --query "value.average")

if (( $(echo "$CPU_UTIL > $THRESHOLD_PERCENT" | bc -l) )); then
  echo "$TIMESTAMP - CPU utilization ($CPU_UTIL%) exceeds threshold ($THRESHOLD_PERCENT%). Resizing VM to '$NEW_SIZE'..."
  az vm resize --resource-group "$RESOURCE_GROUP" --name "$VM_NAME" --size "$NEW_SIZE"
  if [ $? -eq 0 ]; then
    echo "$TIMESTAMP - VM '$VM_NAME' resized successfully to '$NEW_SIZE'."
  else
    echo "$TIMESTAMP - Failed to resize VM '$VM_NAME'."
  fi
else
  echo "$TIMESTAMP - CPU utilization ($CPU_UTIL%) is within acceptable limits."
fi
