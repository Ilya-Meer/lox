#!/usr/bin/ruby

if ARGV.length != 1
  puts "Usage: generate_ast <output directory>"
  exit 64
end

output_directory = ARGV[0]

class Generate_ast
  attr_accessor :output_dir

  def initialize(output_dir)
    @output_dir = output_dir
  end

  def define_ast(base_name, types)
    path = "#{@output_dir}/#{base_name}.java"
    
    file = File.open(path, "w")

    file.puts "package com.craftinginterpreters.lox;"
    file.puts "\n"
    file.puts "import java.util.List;"
    file.puts "\n"
    file.puts "abstract class #{base_name} {"

    types.each do |type|
      class_name = type.split(":")[0].strip
      fields = type.split(":")[1].strip

      define_type(file, base_name, class_name, fields)
    end

    file.puts "}"
  end

  def define_type(file, base_name, class_name, field_list)
    file.puts " static class #{class_name} extends #{base_name} {"
    file.puts "    #{class_name} (#{field_list}) {"

    field_list.split(", ").each do |field|
      field_name = field.split(" ")[1]
      
      file.puts "      this.#{field_name}=#{field_name};"
    end

    file.puts "    }"

    fields.each do |field|
      file.puts "    final #{field};"
    end

    file.puts "  }\n"
  end
end

astGen = Generate_ast.new(output_directory)

astGen.define_ast("Expr", [
  "Binary   : Expr left, Token operator, Expr right",
  "Grouping : Expr expression",
  "Literal  : Object value",
  "Unary    : Token operator, Expr right"
])
