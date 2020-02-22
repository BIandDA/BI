echo -n "AWS_ACCESS_KEY_ID: "
echo -n ${AWS_ACCESS_KEY_ID} | gcloud kms encrypt \
  --plaintext-file=- \
  --ciphertext-file=- \
  --location=global \
  --keyring=aws-key-ring \
  --key=aws-key | base64

echo -n "AWS_SECRET_ACCESS_KEY: "
echo -n ${AWS_SECRET_ACCESS_KEY} | gcloud kms encrypt \
  --plaintext-file=- \
  --ciphertext-file=- \
  --location=global \
  --keyring=aws-key-ring \
  --key=aws-key | base64
