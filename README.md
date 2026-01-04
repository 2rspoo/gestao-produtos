Grupo 136

Contribuições:
Camila Rabello Spoo Goshima - Discord: camilaspoo - 11 973091025
Rodrigo Rabello Spoo - Discord: srsinistro9459 - 11 981046096

Descrição:
Este projeto é um sistema de pedidos fast food, onde o cliente pode escolher os produtos do seu pedido e acompanhar o status em tempo real, sem a necessidade de interação humana direta para o avanço das fases do pedido.

Vídeo:
https://www.youtube.com/watch?v=oYuT7maHG5g

Repositório:
https://github.com/CRASPOO/SistemaPedidos

Arquitetura da Solução
Como arquiteto de software, o design desta solução visa atender aos requisitos de negócio e infraestrutura, garantindo robustez, escalabilidade e observabilidade em um ambiente de desenvolvimento local.

Requisitos de Negócio
Gestão Completa de Pedidos: Capacidade de criar novos pedidos, visualizar o histórico e avançar o status de cada pedido (ex: "recebido", "em preparação", "pronto para entrega").

Processamento de Pagamentos: 
Gerenciamento do status de pagamentos (pendente, aprovado, recusado) via webhook.

Catálogo de Produtos Dinâmico: 
Exibição de produtos organizados por categorias, com informações detalhadas.

Alta Disponibilidade e Resiliência: 
A aplicação deve ser capaz de operar continuamente, mesmo sob condições de falha de componentes.

Escalabilidade Automática (Autoscaling): 
A capacidade de processamento da aplicação deve se adaptar dinamicamente ao volume de requisições, aumentando ou diminuindo o número de instâncias da aplicação conforme a demanda.

Validação de Desempenho: 
Ferramentas integradas para simular picos de tráfego e validar o comportamento de escalabilidade da aplicação.

Requisitos de Infraestrutura (Docker Desktop Local)
A infraestrutura foi projetada para ser executada localmente no Kubernetes do Docker Desktop, um ambiente que replica funcionalidades de um cluster Kubernetes de produção de forma leve e eficiente.

Os componentes-chave da arquitetura são:

Deployment: Gerencia o ciclo de vida dos pods da aplicação (spring-app-deployment) e do banco de dados (postgres-db-deployment). Garante que o número desejado de réplicas esteja sempre em execução e que a aplicação se recupere automaticamente em caso de falhas.

Service: Expõe as aplicações dentro e fora do cluster.

db-service (ClusterIP): Permite que a aplicação Spring Boot se comunique com o banco de dados PostgreSQL internamente no cluster, usando um nome de serviço estável.

spring-app-service (NodePort): Expõe a API da aplicação Spring Boot para acesso externo (ex: navegador, Postman) através de uma porta específica do nó (30001 no Docker Desktop).

HorizontalPodAutoscaler (HPA): O componente central para a escalabilidade automática da aplicação spring-app. Ele monitora a utilização de CPU dos pods da aplicação e, se o uso exceder um limite configurado (ex: 70%), o HPA aumenta o número de réplicas até o máximo definido. Quando a carga diminui, ele reduz as réplicas. O HPA depende do Metrics Server.

Metrics Server: Essencial para o funcionamento do HPA. Ele coleta métricas de uso de recursos (CPU e memória) dos pods e nós do cluster e as disponibiliza para o Kubernetes API Server, que por sua vez as fornece ao HPA.

PersistentVolumeClaim (PVC): O postgres-db-pvc solicita armazenamento persistente para o banco de dados PostgreSQL, garantindo que os dados não sejam perdidos mesmo que o pod do banco de dados seja reiniciado ou movido.

Secrets e ConfigMaps: Utilizados para gerenciar a configuração da aplicação e credenciais de forma segura e desacoplada do código.

db-init-script: Um ConfigMap que contém scripts SQL para inicialização do banco de dados.

Pré-requisitos
Certifique-se de que os seguintes softwares estão instalados e configurados no seu ambiente:

Java Development Kit (JDK): Versão 17 ou superior.

Docker Desktop: Com o Kubernetes habilitado nas configurações (Settings > Kubernetes > Enable Kubernetes).

kubectl: A ferramenta de linha de comando para interagir com o cluster Kubernetes.

Helm: O gerenciador de pacotes do Kubernetes.

Instalação do Chocolatey (para Windows, se não tiver):
Abra o PowerShell como Administrador e execute:
Set-ExecutionPolicy Bypass -Scope Process -Force; [System.Net.ServicePointManager]::SecurityProtocol = [System.Net.ServicePointManager]::SecurityProtocol -bor 3072; iex ((New-Object System.Net.WebClient).DownloadString('https://community.chocolatey.org/install.ps1'))
Importante: Feche e reabra o PowerShell como Administrador após a instalação do Chocolatey.
Instalação do Helm (após Chocolatey):
Abra o PowerShell como Administrador e execute:
choco install kubernetes-helm
Importante: Feche e reabra o PowerShell como Administrador novamente para que o comando helm seja reconhecido.

Instalação Aplicação
Siga estes passos para configurar e implantar o projeto no seu cluster Kubernetes local.

Clone o repositório:
git clone https://github.com/CRASPOO/SistemaPedidos

Construa a imagem Docker da sua aplicação Spring Boot:
Navegue até o diretório raiz do seu projeto Spring Boot (onde está o Dockerfile).
docker build -t order .

Após a construção da imagem, volte para o diretório kubernetes:
Implante os recursos do Kubernetes (Ordem Importante!):

Recursos da Base de Dados (PostgreSQL):

kubectl apply -f db-secrets.yaml
kubectl apply -f db-config.yaml
kubectl apply -f db-init-config.yaml
kubectl apply -f postgres-pvc.yaml
kubectl apply -f db-deployment.yaml
kubectl apply -f db-service.yaml

Recursos da Aplicação Spring Boot:

kubectl apply -f api-deployment.yaml
kubectl apply -f api-service.yaml

Instale o Metrics Server (via Helm):

helm repo add metrics-server https://kubernetes-sigs.github.io/metrics-server/
helm install metrics-server metrics-server/metrics-server --version 3.11.0 --namespace kube-system --set "args={'--kubelet-insecure-tls','--kubelet-preferred-address-types=InternalIP'}"
Aguarde cerca de 1 a 2 minutos para o pod do Metrics Server subir e começar a coletar métricas.

Implante o Horizontal Pod Autoscaler (HPA):

kubectl apply -f spring-app-hpa.yaml

Verifique o status de todos os pods:

kubectl get pods -A

Aguarde até que todos os pods (incluindo postgres-db, spring-app e metrics-server no namespace kube-system) estejam com o status Running.

Inicialize o banco de dados:

Execute o script SQL principal para popular o banco de dados. Você pode usar um cliente PostgreSQL (como o psql ou DBeaver) e conectar-se ao banco de dados exposto pelo Kubernetes (geralmente localhost:30001 se você configurou o serviço do banco de dados para NodePort, ou acessar via kubectl port-forward).
Se carga do bando de dados não for feita automático   script está localizado em: SistemaPedidos\script\script.sql

Acesso ao Frontend da Aplicação:

Abra o arquivo index.html, webhook ou stress.html diretamente no seu navegador. As interfaces carregarão os dados da API.

Acesso a Documentação da API (Swagger UI):

A documentação interativa completa da API está disponível em:
http://localhost:30001/swagger-ui.html

Teste de Estresse e Validação de Autoscaling (Opcional):

Na página stress.html, clique no botão "Iniciar Teste Simples" para simular uma carga intensa de requisições na sua API.
Em um terminal separado, monitore o comportamento do HPA em tempo real:
kubectl get hpa spring-app-hpa -w
Observe como a coluna TARGETS (utilização de CPU) aumenta e, em resposta, a coluna REPLICAS (número de pods) irá escalar automaticamente para lidar com a carga.



