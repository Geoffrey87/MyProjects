using AutoMapper;
using BankingApp.API.DTOs.RequestDtos;
using BankingApp.API.DTOs.ResponseDtos;
using BankingApp.API.Entities;
using BankingApp.API.Exceptions;
using BankingApp.API.Exceptions.Custom;
using BankingApp.API.Repositories.Interfaces;
using BankingApp.API.Services.Interfaces;

namespace BankingApp.API.Services.Implementations
{
    public class BeneficiaryService : IBeneficiaryService
    {
        private readonly IBeneficiaryRepository _beneficiaryRepository;
        private readonly IMapper _mapper;

        public BeneficiaryService(IBeneficiaryRepository beneficiaryRepository, IMapper mapper)
        {
            _beneficiaryRepository = beneficiaryRepository;
            _mapper = mapper;
        }

        public async Task<List<BeneficiaryResponseDto>> GetAllAsync()
        {
            var beneficiaries = await _beneficiaryRepository.GetAllAsync();
            return _mapper.Map<List<BeneficiaryResponseDto>>(beneficiaries);
        }

        public async Task<BeneficiaryResponseDto> GetByIdAsync(int id)
        {
            var beneficiary = await _beneficiaryRepository.GetByIdAsync(id)
                ?? throw new NotFoundException(ErrorMessages.BeneficiaryNotFound);
            return _mapper.Map<BeneficiaryResponseDto>(beneficiary);
        }

        public async Task<List<BeneficiaryResponseDto>> GetByUserIdAsync(int userId)
        {
            var beneficiaries = await _beneficiaryRepository.GetByUserIdAsync(userId);
            if (beneficiaries == null || !beneficiaries.Any())
                throw new NotFoundException(ErrorMessages.BeneficiaryNotFound);
            return _mapper.Map<List<BeneficiaryResponseDto>>(beneficiaries);
        }

        public async Task<BeneficiaryResponseDto> CreateAsync(BeneficiaryRequestDto dto)
        {
            await ExistsByIBANAsync(dto.UserId, dto.IBAN);
            var beneficiary = _mapper.Map<Beneficiary>(dto);
            await _beneficiaryRepository.AddAsync(beneficiary);
            return _mapper.Map<BeneficiaryResponseDto>(beneficiary);
        }

        public async Task<BeneficiaryResponseDto> UpdateAsync(int id, BeneficiaryRequestDto dto)
        {
            var beneficiary = await _beneficiaryRepository.GetByIdAsync(id)
                ?? throw new NotFoundException(ErrorMessages.BeneficiaryNotFound);
            _mapper.Map(dto, beneficiary);
            await _beneficiaryRepository.UpdateAsync(beneficiary);
            return _mapper.Map<BeneficiaryResponseDto>(beneficiary);
        }

        public async Task DeleteAsync(int id)
        {
            var beneficiary = await _beneficiaryRepository.GetByIdAsync(id)
                ?? throw new NotFoundException(ErrorMessages.BeneficiaryNotFound);
            await _beneficiaryRepository.DeleteAsync(beneficiary.Id);
        }

        public async Task<bool> ExistsByIBANAsync(int userId, string iban)
        {
            if (await _beneficiaryRepository.ExistsByIBANAsync(userId, iban))
                throw new ConflictException(ErrorMessages.BeneficiaryAlreadyExists);
            return false;
        }
    }
    }