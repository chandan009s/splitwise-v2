resource "aws_instance" "app" {
  ami           = var.ami_id
  instance_type = var.instance_type
  subnet_id     = aws_subnet.public.id

  key_name               = aws_key_pair.ssh_key.key_name
  vpc_security_group_ids = [aws_security_group.app_sg.id]

  tags = {
    Name = "${var.project_name}-ec2"
  }
}

