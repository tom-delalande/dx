package main

import (
	"fmt"
	"log"
	"os"

	"github.com/spf13/cobra"
	"gopkg.in/yaml.v3"
)

type Config struct {
	Name string `yaml:"name"`
	Up   struct {
		Dependencies []string `yaml:",flow"`
	}
}

func main() {
	rootCmd.AddCommand(versionCmd)
	rootCmd.AddCommand(upCmd)
	if err := rootCmd.Execute(); err != nil {
		fmt.Println(err)
		os.Exit(1)
	}
}

var rootCmd = &cobra.Command{
	Use:   "dx",
	Short: "dx is a tiny tool",
	Long:  ``,
}

var versionCmd = &cobra.Command{
	Use:   "version",
	Short: "Print the version number of dx",
	Long:  `All software has versions. This is dx's`,
	Run: func(cmd *cobra.Command, args []string) {
		fmt.Println("0.0.0")
	},
}

var upCmd = &cobra.Command{
	Use:   "up",
	Short: "Install dependencies for the current project",
	Long:  `Install dependencies for the current project`,
	Run: func(cmd *cobra.Command, args []string) {
		data, err := os.ReadFile("./dx.yml")
		if err != nil {
			log.Fatalf("%v", err)
		}
		config := Config{}
		err = yaml.Unmarshal([]byte(data), &config)
		if err != nil {
			log.Fatalf("%v", err)
		}
		fmt.Printf("%v", config.Up.Dependencies)
	},
}
