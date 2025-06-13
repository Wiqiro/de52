#!/bin/bash

# Script de test pour tous les endpoints de l'API Movie
# Assurez-vous que l'application Quarkus est démarrée avant d'exécuter ce script

BASE_URL="http://localhost:8080"
API_URL="$BASE_URL/movies"

echo "=== Test des endpoints de l'API Movie ==="
echo ""

# Fonction pour tester un endpoint
test_endpoint() {
    local method=$1
    local url=$2
    local description=$3
    local data=$4
    local expected_status=$5
    
    echo "Test: $description"
    echo "URL: $method $url"
    
    if [ -n "$data" ]; then
        response=$(curl -s -w "\n%{http_code}" -X $method -H "Content-Type: application/json" -d "$data" "$url")
    else
        response=$(curl -s -w "\n%{http_code}" -X $method "$url")
    fi
    
    # Séparer le body et le status code
    body=$(echo "$response" | head -n -1)
    status_code=$(echo "$response" | tail -n 1)
    
    echo "Status Code: $status_code"
    if [ ${#body} -gt 200 ]; then
        echo "Response: $(echo "$body" | cut -c1-200)..."
    else
        echo "Response: $body"
    fi
    
    if [ "$status_code" = "$expected_status" ]; then
        echo "✓ PASS"
    else
        echo "✗ FAIL (Expected: $expected_status, Got: $status_code)"
    fi
    echo "---"
}

# 1. Test GET /movies - Récupérer tous les films
test_endpoint "GET" "$API_URL" "Récupérer tous les films" "" "200"

# 2. Test GET /movies/{id} - Récupérer un film existant (utiliser les IDs négatifs)
test_endpoint "GET" "$API_URL/-1" "Récupérer le film Titanic (ID -1)" "" "200"

# 3. Test GET /movies/{id} - Récupérer un film qui n'existe pas
test_endpoint "GET" "$API_URL/999" "Récupérer un film inexistant (ID 999)" "" "404"

# 4. Test GET /movies/{id}/actors - Récupérer les acteurs d'un film
test_endpoint "GET" "$API_URL/-1/actors" "Récupérer les acteurs du film Titanic (ID -1)" "" "200"

# 5. Test GET /movies/{id}/actors - Film inexistant
test_endpoint "GET" "$API_URL/999/actors" "Récupérer les acteurs d'un film inexistant" "" "404"

# 6. Test POST /movies - Ajouter un nouveau film
new_movie='{"title":"Test Movie","copies":5,"movieType":"Action","mainActor":{"id":-1,"firstname":"Louis","lastname":"De Funes"}}'
test_endpoint "POST" "$API_URL" "Ajouter un nouveau film" "$new_movie" "201"

# 7. Test POST /movies/{movieId}/actors - Ajouter un acteur à un film
new_actor='{"firstname":"John","lastname":"Doe"}'
test_endpoint "POST" "$API_URL/-1/actors" "Ajouter un acteur au film Titanic" "$new_actor" "201"

# 8. Test POST /movies/{movieId}/actors - Film inexistant
test_endpoint "POST" "$API_URL/999/actors" "Ajouter un acteur à un film inexistant" "$new_actor" "404"

# 9. Test DELETE /movies/{movieId}/actors/{actorId} - Supprimer un acteur d'un film
test_endpoint "DELETE" "$API_URL/-1/actors/-9" "Supprimer Kate Winslett du film Titanic" "" "204"

# 10. Test DELETE /movies/{movieId}/actors/{actorId} - Acteur/Film inexistant
test_endpoint "DELETE" "$API_URL/999/actors/999" "Supprimer un acteur inexistant d'un film inexistant" "" "404"

# 11. Test DELETE /movies/{id} - Supprimer un film existant
test_endpoint "DELETE" "$API_URL/-9" "Supprimer le film Pretty Woman (ID -9)" "" "204"

# 12. Test DELETE /movies/{id} - Supprimer un film inexistant
test_endpoint "DELETE" "$API_URL/999" "Supprimer un film inexistant" "" "404"

# 13. Vérifier que le film supprimé n'existe plus
test_endpoint "GET" "$API_URL/-9" "Vérifier que Pretty Woman a été supprimé" "" "404"

echo ""
echo "=== Tests terminés ==="
echo ""
echo "Note: Ces tests modifient les données. Redémarrez l'application pour restaurer les données initiales."
