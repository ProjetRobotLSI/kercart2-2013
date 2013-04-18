#!/bin/bash

echo "$1" | grep $2 -B 2 | head -n 1 | awk '{print $1}'
