variable "aws_region" {
  default = "us-east-1"
}

variable "project_name" {
  default = "splitwise"
}

variable "ami_id" {
  description = "Ubuntu Linux Ami"
  default     = "ami-0ecb62995f68bb549"
}

variable "instance_type" {
  default = "t2.micro"
}

variable "key_name" {
  description = "EC2 SSH key pair name"
}

variable "db_username" {}

variable "db_password" {
  sensitive = true
}

