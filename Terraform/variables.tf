variable "aws_region" {
  default = "us-east-1"
}

variable "project_name" {
  default = "splitwise"
}

variable "ami_id" {
  description = "Amazon Linux 2 AMI"
  default     = "ami-0c02fb55956c7d316"
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

