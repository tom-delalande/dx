FROM nixos/nix

RUN nix-env -iA nixpkgs.go nixpkgs.air

WORKDIR /app
ENV GOFLAGS "-buildvcs=false"

CMD air
