#!/bin/bash

# URL base da aplicação
BASE_URL="http://localhost:8080"

# Função para testar criação de cliente
create_customer() {
  echo "Criando cliente..."
  CUSTOMER_RESPONSE=$(curl -s -X POST "$BASE_URL/customers" -H "Content-Type: application/json" -d '{
    "email": "'"$1"'",
    "nome": "'"$2"'"
  }')

  CUSTOMER_ID=$(echo "$CUSTOMER_RESPONSE" | jq -r '.id')
  echo "Cliente criado com ID: $CUSTOMER_ID"
  echo "==============================="
}

# Função para listar todos os clientes
list_customers() {
  echo "Listando todos os clientes..."
  curl -s -X GET "$BASE_URL/customers" | jq .
  echo "==============================="
}

# Função para criar uma ordem
create_order() {
  echo "Criando ordem para o cliente ID $1..."
  curl -s -X POST "$BASE_URL/orders" -H "Content-Type: application/json" -d '{
    "description": "'"$2"'",
    "price": '"$3"',
    "customerId": '"$1"'
  }' | jq .
  echo "==============================="
}

# Função para listar todas as ordens
list_orders() {
  echo "Listando todas as ordens..."
  curl -s -X GET "$BASE_URL/orders" | jq .
  echo "==============================="
}

# Teste 1: Criar e listar clientes
create_customer "customer@example.com" "Cliente Teste"
list_customers

create_customer "newcustomer@example.com" "Novo Cliente"
list_customers

# Teste 2: Criar e listar ordens para os clientes
create_order "$CUSTOMER_ID" "Pedido de teste" 100.00
create_order "$CUSTOMER_ID" "Segundo pedido" 150.00

# Criar ordem para o segundo cliente (substitua o ID conforme necessário)
SECOND_CUSTOMER_ID=2
create_order "$SECOND_CUSTOMER_ID" "Pedido para novo cliente" 200.00

# Teste 3: Listar todas as ordens
list_orders

echo "Todos os testes foram executados."
