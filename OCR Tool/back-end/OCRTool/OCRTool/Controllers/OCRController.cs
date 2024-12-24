using Microsoft.AspNetCore.Mvc;
using OCRTool.Models;
using OCRTool.Services;

namespace OCRTool.Controllers
{
    [ApiController]
    [Route("api/ocr")]
    public class OCRController : Controller
    {
        private readonly OCRService _ocrService;

        public OCRController()
        {
            _ocrService = new OCRService();
        }

        [HttpPost("upload")]
        public async Task<IActionResult> UploadImage(IFormFile file)
        {
            if (file == null || file.Length == 0)
                return BadRequest("No file uploaded.");

            await using var stream = file.OpenReadStream();
            var extractedText = _ocrService.ProcessImage(stream);

            return Ok(new OCRResult { Text = extractedText });
        }
    }
}
