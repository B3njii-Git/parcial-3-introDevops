terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.0"
    }
  }
}

provider "aws" {
  region = "us-east-1"
}

data "aws_caller_identity" "current" {}

locals {
  lab_role_arn = "arn:aws:iam::${data.aws_caller_identity.current.account_id}:role/LabRole"
}

# Usaremos la VPC existente que creaste a mano o en la fase anterior
data "aws_vpc" "main" {
  filter {
    name   = "tag:Name"
    values = ["bomberos-vpc"]
  }
}

data "aws_subnets" "private" {
  filter {
    name   = "vpc-id"
    values = [data.aws_vpc.main.id]
  }
  filter {
    name   = "tag:Name"
    values = ["*private*"]
  }
}

data "aws_security_group" "ecs" {
  name = "ECS-Tasks-SG"
}

# ---------------------------------------------------------
# 1. BASE DE DATOS RDS (PostgreSQL)
# ---------------------------------------------------------
resource "aws_security_group" "rds" {
  name        = "RDS-SG"
  description = "Permitir trafico de DB desde contenedores ECS"
  vpc_id      = data.aws_vpc.main.id

  ingress {
    from_port       = 5432
    to_port         = 5432
    protocol        = "tcp"
    security_groups = [data.aws_security_group.ecs.id]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "aws_db_subnet_group" "rds" {
  name       = "bomberos-db-subnet-group"
  subnet_ids = data.aws_subnets.private.ids
  tags = { Name = "bomberos-db-subnet-group" }
}

resource "aws_db_instance" "postgres" {
  identifier             = "bomberos-db"
  engine                 = "postgres"
  engine_version         = "16.3" # Versión genérica de Postgres
  instance_class         = "db.t3.micro"
  allocated_storage      = 20
  db_name                = "bomberos_db"
  username               = "postgres"
  password               = "Bomberos2026"
  db_subnet_group_name   = aws_db_subnet_group.rds.name
  vpc_security_group_ids = [aws_security_group.rds.id]
  skip_final_snapshot    = true
  publicly_accessible    = false
}

# ---------------------------------------------------------
# 2. NUEVOS REPOSITORIOS ECR
# ---------------------------------------------------------
resource "aws_ecr_repository" "eureka" {
  name                 = "eureka-repo"
  force_delete         = true
}

resource "aws_ecr_repository" "usuarios" {
  name                 = "usuarios-repo"
  force_delete         = true
}

resource "aws_ssm_parameter" "db_url" {
  name  = "DB_URL"
  type  = "String"
  value = "jdbc:postgresql://${aws_db_instance.postgres.endpoint}/${aws_db_instance.postgres.db_name}"
}

output "db_endpoint" {
  value = aws_db_instance.postgres.endpoint
}
