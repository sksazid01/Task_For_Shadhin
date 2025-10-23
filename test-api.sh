#!/bin/bash

# Task Management API - Testing Script
# This script tests all API endpoints

BASE_URL="http://localhost:8080"

echo "=========================================="
echo "Task Management API - Testing Script"
echo "=========================================="
echo ""

# Check if server is running
echo "1. Checking if server is running..."
if curl -s "$BASE_URL/tasks" > /dev/null 2>&1; then
    echo "✓ Server is running"
else
    echo "✗ Server is not running. Please start the application first."
    exit 1
fi
echo ""

# Create tasks
echo "2. Creating tasks..."
echo "Creating Task 1 (Priority 5):"
curl -X POST "$BASE_URL/tasks" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Complete Spring Boot Project",
    "description": "Develop REST API with CRUD operations",
    "priority": 5,
    "completed": false
  }'
echo -e "\n"

echo "Creating Task 2 (Priority 4):"
curl -X POST "$BASE_URL/tasks" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Write Documentation",
    "description": "Create comprehensive README",
    "priority": 4,
    "completed": false
  }'
echo -e "\n"

echo "Creating Task 3 (Priority 3):"
curl -X POST "$BASE_URL/tasks" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Test API Endpoints",
    "description": "Test all endpoints using Postman",
    "priority": 3,
    "completed": false
  }'
echo -e "\n\n"

# Get all tasks
echo "3. Getting all tasks..."
curl -X GET "$BASE_URL/tasks"
echo -e "\n\n"

# Get task by ID
echo "4. Getting task by ID (ID=1)..."
curl -X GET "$BASE_URL/tasks/1"
echo -e "\n\n"

# Mark task as completed
echo "5. Marking task as completed (ID=1)..."
curl -X PUT "$BASE_URL/tasks/1/complete"
echo -e "\n\n"

# Get highest priority task
echo "6. Getting highest priority task..."
curl -X GET "$BASE_URL/tasks/highest-priority"
echo -e "\n\n"

# Get task statistics
echo "7. Getting task statistics..."
curl -X GET "$BASE_URL/tasks/stats"
echo -e "\n\n"

# Delete a task
echo "8. Deleting task (ID=3)..."
curl -X DELETE "$BASE_URL/tasks/3" -v
echo -e "\n\n"

# Get all tasks again to verify deletion
echo "9. Getting all tasks after deletion..."
curl -X GET "$BASE_URL/tasks"
echo -e "\n\n"

echo "=========================================="
echo "Testing completed!"
echo "=========================================="
