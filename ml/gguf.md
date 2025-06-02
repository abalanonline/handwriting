### HF to GGUF

download the model

https://huggingface.co/EleutherAI/gpt-j-6b/tree/main

```bash
sudo pacman -S cmake
git clone https://github.com/ggerganov/llama.cpp.git
cd llama.cpp/
python -m venv venv
source venv/bin/activate
pip install -r requirements.txt
python convert_hf_to_gguf.py
```
