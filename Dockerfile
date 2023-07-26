FROM nixos/nix

RUN nix-env -iA nixpkgs.go

WORKDIR /app

COPY . .

CMD go run main.go
