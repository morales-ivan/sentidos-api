setup:
	docker-compose build

dev:
	AWS_ACCESS_KEY=$(AWS_ACCESS_KEY) \
	AWS_SECRET_KEY=$(AWS_SECRET_KEY) \
	AWS_REGION=$(AWS_REGION) \
	AWS_BUCKET=$(AWS_BUCKET) \
	docker-compose up

down:
	docker-compose down
