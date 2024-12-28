using Microsoft.AspNetCore.Mvc;
using System.Text.Json;

namespace Json_Parser.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class JsonParserController : ControllerBase
    {
        [HttpPost("validate")]
        public IActionResult ValidateJson([FromBody] JsonElement json)
        {
            try
            {
                JsonDocument.Parse(json.GetRawText());
                return Ok(new { valid = true, message = "Valid JSON" });
            }
            catch (JsonException ex)
            {
                return BadRequest(new { valid = false, message = $"Invalid JSON: {ex.Message}" });
            }
        }

        [HttpPost("parse")]
        public IActionResult ParseJson([FromBody] JsonElement json)
        {
            try
            {
                return Ok(json);
            }
            catch (JsonException ex)
            {
                return BadRequest(new { valid = false, message = $"Invalid JSON: {ex.Message}" });
            }
        }
    }
}
