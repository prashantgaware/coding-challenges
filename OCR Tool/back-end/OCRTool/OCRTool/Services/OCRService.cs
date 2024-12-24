using Tesseract;

namespace OCRTool.Services
{
    public class OCRService
    {
        private const string TesseractDataPath = "D:\\Practice\\coding-challenges\\OCR Tool\\back-end\\OCRTool\\OCRTool\\tessdata\\"; 
        private const string Language = "eng"; 

        public string ProcessImage(Stream imageStream)
        {
            using var engine = new TesseractEngine(TesseractDataPath, Language, EngineMode.Default);
            using var img = Pix.LoadFromMemory(ReadStream(imageStream));

            var result = engine.Process(img);
            return result.GetText();
        }

        private byte[] ReadStream(Stream input)
        {
            using (var ms = new MemoryStream())
            {
                input.CopyTo(ms);
                return ms.ToArray();
            }
        }
    }
}
