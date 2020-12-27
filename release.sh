#!/bin/bash
shadow-cljs release frontend --config-merge '{:output-dir "release"}'
cp public/main.css release/main.css
